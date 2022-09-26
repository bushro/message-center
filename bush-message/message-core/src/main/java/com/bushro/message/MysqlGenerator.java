//package com.bushro.message;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
//import com.baomidou.mybatisplus.generator.config.po.TableFill;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.bushro.common.mybatis.MyTemplateEngine;
//import com.bushro.common.mybatis.base.BaseService;
//import com.bushro.common.mybatis.base.BaseServiceImpl;
//import com.bushro.common.mybatis.base.MybatisMapper;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * <p>
// * mysql 代码生成器演示例子
// * </p>
// *
// * @author jobob
// * @since 2018-09-12
// */
//public class MysqlGenerator {
//
//    /**
//     * <p>
//     * 读取控制台内容
//     * </p>
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotEmpty(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }
//
//    /**
//     * RUN THIS
//     */
//    public static void main(String[] args) {
//        //String module = "/ssa-config";
//
//        String packageName = MysqlGenerator.class.getPackage().getName();
//        int lastIndex = packageName.lastIndexOf(".");
//        String classPath =   MysqlGenerator.class.getClassLoader().getResource("").getPath();
//        int targetIndex = classPath.indexOf("target");
//        String root = classPath.substring(0,targetIndex);
//
//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName( packageName.substring(lastIndex+1));
//        pc.setParent(packageName.substring(0,lastIndex) );
//        mpg.setPackageInfo(pc);
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        gc.setIdType(IdType.ID_WORKER);
//        gc.setOutputDir(root+"src/main/java");
//        gc.setAuthor("asiainfo");
//        gc.setOpen(false);
//        gc.setSwagger2(true);
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://192.168.1.234:6033/MAXS5?useUnicode=true&useSSL=false&characterEncoding=utf8");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("1qazXSW@3edc");
//        mpg.setDataSource(dsc);
//
//        // 包配置
//
//        String javaPath = root+"src/main/java/"+pc.getParent().replaceAll("[.]","/");
//        // 自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                // to do nothing
//            }
//        };
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return root+"src/main/resources/mapper/" + pc.getModuleName()
//                    + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        });
//
//        focList.add(new FileOutConfig("/templates/rpc.java.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return javaPath +"/rpc"
//                    + "/" + tableInfo.getEntityName() + "Rpc.java" ;
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//        mpg.setTemplate(new TemplateConfig().setXml(null));
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        // strategy.setSuperEntityClass("ssa.web.mybatis.ssa.web.message.entity.BaseEntity");
//        strategy.setEntityLombokModel(false);
//        strategy.setSuperServiceClass(BaseService.class.getName());
//        strategy.setSuperEntityClass("ssa.web.common.entity.BaseEntity");
//        strategy.setSuperMapperClass(MybatisMapper.class.getName());
//        strategy.setSuperServiceImplClass(BaseServiceImpl.class.getName());
//        // strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
//        //   strategy.setInclude("UPMS_AUTH_MENU","UPMS_AUTH_DETAIL","UPMS_RESTFUL","UPMS_USER","UPMS_MENU","UPMS_SYSTEM","UPMS_TENANT","UPMS_PERMISSION_ROLE","UPMS_USER_ROLE","UPMS_ORGANIZATION","UPMS_PERMISSION","UPMS_ROLE","UPMS_PERMISSION","UPMS_RESTFUL");
//        //      strategy.setInclude("SSA_APP","SSA_CONTEXT","SSA_FLOW","SSA_JOB","SSA_JOBSERVER_PARAMS","SSA_MODEL_GUIDE","SSA_OPERATOR_DETAIL","SSA_SCENE_GUIDE","SSA_TASK");
//        //  strategy.setInclude("UPMS_USER","UPMS_ROLE","UPMS_DOMAIN","UPMS_ORG_PERMISSION","UPMS_ORGANIZATION","UPMS_PERMISSION","UPMS_ROLE_ACL","UPMS_ROLE_PERMISSION"
//        //,"UPMS_TENANT","UPMS_USER_ORGANIZATION","UPMS_USER_PERMISSION","UPMS_USER_ROLE","UPMS_MENU");
//         strategy.setInclude("MESSAGE_CONFIG","MESSAGE_CONFIG_VALUE","MESSAGE_REQUEST","MESSAGE_REQUEST_DETAIL");
////        strategy.setInclude( "SYS_CFG","SYS_DICT","SYS_FILE","SYS_FILE_HOST","UPMS_PERMISSION_ACTION","UPMS_PERMISSION_DATA","UPMS_DOMAIN_TYPE","UPMS_PERMISSION_FIELD",
////            "UPMS_MENU","UPMS_MODULE","UPMS_MODULE_ACTION","UPMS_MODULE_FIELD","UPMS_ORGANIZATION","UPMS_ORGANIZATION_MENU","UPMS_ROLE","UPMS_SERVICE_API","UPMS_TENANT"
////            ,"UPMS_TENANT_IP","UPMS_TENANT_MENU","UPMS_USER","UPMS_USER_ORGANIZATION","UPMS_USER_ROLE");
//        strategy.setEntityLombokModel(true);
//        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setRestControllerStyle(true);
//        strategy.setTablePrefix("");
//        List<TableFill> fillList = new ArrayList<>();
//      //  fillList.add(new TableFill("CREATE_TIME", FieldFill.INSERT));
//        strategy.setTableFillList(fillList);
//        mpg.setStrategy(strategy);
//        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
//        mpg.setTemplateEngine(new MyTemplateEngine());
//        ConfigBuilder config = new ConfigBuilder(pc, dsc, strategy, new TemplateConfig().setXml(null), gc);
//        config.getPackageInfo().put("Parent",pc.getParent());
//        config.getPackageInfo().put("Dao",pc.getParent()+".dao");
//        config.getPackageInfo().put("Rpc",pc.getParent()+".rpc");
//        config.getPackageInfo().put("MyService",pc.getParent()+".service");
//        config.getPackageInfo().put("MyServiceImpl",pc.getParent()+".service.impl");
//        mpg.setConfig(config);
//        mpg.execute();
//    }
//
//}
