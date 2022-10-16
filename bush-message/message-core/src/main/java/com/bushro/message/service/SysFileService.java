package com.bushro.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bushro.common.core.util.R;
import com.bushro.message.entity.SysFile;
import com.bushro.message.vo.RemoteFileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * sys文件服务
 *
 * @author luo.qiang
 * @date 2022/10/16
 */
public interface SysFileService extends IService<SysFile> {

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	R<RemoteFileVo> uploadFile(MultipartFile file);

	/**
	 * 读取文件
	 * @param bucket 桶名称
	 * @param fileName 文件名称
	 * @param response 输出流
	 */
	void getFile(String bucket, String fileName, HttpServletResponse response);

	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	Boolean deleteFile(Long id);

}
