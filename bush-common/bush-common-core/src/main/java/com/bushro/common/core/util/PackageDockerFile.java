package com.bushro.common.core.util;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class PackageDockerFile {
    public static void main(String[] args) {
        String classPath = PackageDockerFile.class.getClassLoader().getResource("").getPath();
        String root = classPath.substring(0,classPath.indexOf("ssa-common")) ;
        List<File> result = forkFile(root);
        String exportRoot = root+"/docker-export";
        File export = new File(exportRoot);
        if(!export.exists()){
            export.mkdir();
        }
        List<File> exportFiles =  forkFile( exportRoot,"*");

        exportFiles.forEach(f->FileUtil.del(f));

        result.forEach(r->{
           String[] pathArr =  r.getAbsolutePath().substring(root.length()-1).split("\\\\");
           String exportPath = exportRoot;
           String resourcePath = root;
           for (String path : pathArr){
               if(resourcePath.endsWith("/")){
                   resourcePath = resourcePath.substring(0,resourcePath.length()-1);
               }
               resourcePath = resourcePath+"/"+path;
               exportPath = exportPath+"/"+path;
               File e = new File(exportPath);
               File res = new File(resourcePath);
                if(res.isDirectory() && !e.exists()){
                    e.mkdir();
                }

                if(!res.isDirectory()){
                    FileUtil.copy(res,e,true);
                }
           }
        });

        File composeFile = new File(root+"docker-compose.yml");
        File composeFileCopy = new File(exportRoot+"/"+"docker-compose.yml");
        FileUtil.copy(composeFile,composeFileCopy,true);
    }

    public static List<File> forkFile(String path, String fileType){
        ForkJoinPool pool = new ForkJoinPool();
        ForkFileTask task = new ForkFileTask(path,fileType);
        Future<List<File>> result = pool.submit(task);
        try {
            return 	result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<File> forkFile(String path){
        ForkJoinPool pool = new ForkJoinPool();
        ForkDockerTask task = new ForkDockerTask(path,true);
        Future<List<File>> result = pool.submit(task);
        try {
            return 	result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    static class ForkDockerTask  extends RecursiveTask<List<File>> {

        String path;
        boolean isRoot;
        public ForkDockerTask(String path) {
            this.path = path;
            this.isRoot = false;
        }
        public ForkDockerTask(String path,boolean isRoot) {
            this.path = path;
            this.isRoot = isRoot;
        }

        @Override
        protected List<File> compute() {
            List<File>result = new ArrayList<>();
            List<ForkDockerTask> taskList = new ArrayList<>();
            File root = new File(path);
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                String name = f.getName();
                if(f.isDirectory()){
                    if((isRoot && name.startsWith("ssa-") && !name.equals("ssa-common")) || !isRoot){
                        ForkDockerTask task = new ForkDockerTask( f.getPath());
                        taskList.add(task);
                    }
                }else{
                    if((f.getParentFile().getName().equals("target") && f.getName().endsWith(".jar"))|| f.getName().equals("Dockerfile")){
                        result.add(f);
                    }
                }
            }

            if(taskList.size()==0){
                return result;
            }

            ForkDockerTask[] taskArr = new ForkDockerTask[taskList.size()];
            taskArr = taskList.toArray(taskArr);
            invokeAll(taskArr);
            for (int i = 0; i <taskArr.length ; i++) {
                ForkDockerTask task = taskArr[i];
                List<File> taskResult = task.join();
                result.addAll(taskResult);
            }
            return result;

        }
    }


    static class ForkFileTask  extends RecursiveTask<List<File>>{

        String fileType;
        String path;
        public ForkFileTask(String path, String fileType) {
            this.fileType = fileType;
            this.path = path;
        }

        @Override
        protected List<File> compute() {
            List<File>result = new ArrayList<>();
            List<ForkFileTask> taskList = new ArrayList<>();
            File root = new File(path);
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if(f.isDirectory()){
                    ForkFileTask task = new ForkFileTask( f.getPath(), fileType);
                    taskList.add(task);
                    if(fileType.equals("*") || fileType.equals("*d")){
                        result.add(f);
                    }
                }else{
                    if(fileType.equals("*") || fileType.equals("*f")){
                        result.add(f);
                    }else if( f.getName().lastIndexOf("."+fileType) > 0
                            && f.getName().lastIndexOf("."+fileType) == (f.getName().length()-fileType.length()-1)){
                        result.add(f);
                    }
                }


            }

            if(taskList.size()==0){
                return result;
            }

            ForkFileTask[] taskArr = new ForkFileTask[taskList.size()];
            taskArr = taskList.toArray(taskArr);
            invokeAll(taskArr);
            for (int i = 0; i <taskArr.length ; i++) {
                ForkFileTask task = taskArr[i];
                List<File> taskResult = task.join();
                result.addAll(taskResult);
            }
            return result;

        }
    }
}
