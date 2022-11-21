package com.bushro.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bushro.common.core.util.R;
import com.bushro.common.oss.OssProperties;
import com.bushro.common.oss.service.OssTemplate;

import com.bushro.system.entity.SysFile;
import com.bushro.system.mapper.SysFileMapper;
import com.bushro.system.service.ISysFileService;
import com.bushro.system.vo.RemoteFileVo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;


/**
 * sys文件服务impl
 *
 * @author luo.qiang
 * @date 2022/10/16
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements ISysFileService {

	private final OssProperties ossProperties;

	private final OssTemplate ossTemplate;

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	@Override
	public R<RemoteFileVo> uploadFile(MultipartFile file) {
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		RemoteFileVo fileVo = new RemoteFileVo();
		fileVo.setBucket(ossProperties.getBucketName());
		fileVo.setFileName(fileName);
		try {
			ossTemplate.putObject(ossProperties.getBucketName(), fileName, file.getInputStream(),
					file.getContentType());
			// 文件管理数据记录,收集管理追踪文件
			fileLog(file, fileName);
		}
		catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(fileVo);
	}

	/**
	 * 读取文件
	 * @param bucket
	 * @param fileName
	 * @param response
	 */
	@Override
	public void getFile(String bucket, String fileName, HttpServletResponse response) {
		try (S3Object s3Object = ossTemplate.getObject(bucket, fileName)) {
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentType("application/octet-stream; charset=UTF-8");
			IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
		}
		catch (Exception e) {
			log.error("文件读取异常: {}", e.getLocalizedMessage());
		}
	}

	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteFile(Long id) {
		SysFile file = this.getById(id);
		ossTemplate.removeObject(ossProperties.getBucketName(), file.getFileName());
		return this.removeById(id);
	}

	/**
	 * 文件管理数据记录,收集管理追踪文件
	 * @param file 上传文件格式
	 * @param fileName 文件名
	 */
	private void fileLog(MultipartFile file, String fileName) {
		SysFile sysFile = new SysFile();
		sysFile.setFileName(fileName);
		sysFile.setOriginal(file.getOriginalFilename());
		sysFile.setFileSize(file.getSize());
		sysFile.setType(FileUtil.extName(file.getOriginalFilename()));
		sysFile.setBucketName(ossProperties.getBucketName());
		sysFile.setCreateBy("admin");
		sysFile.setUpdateBy("admin");
		sysFile.setDelFlag("0");
		sysFile.setCreateTime(LocalDateTime.now());
		this.save(sysFile);
	}

}
