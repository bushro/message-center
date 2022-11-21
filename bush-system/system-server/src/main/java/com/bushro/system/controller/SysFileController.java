package com.bushro.system.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bushro.common.core.util.R;
import com.bushro.system.entity.SysFile;
import com.bushro.system.service.ISysFileService;
import com.bushro.system.vo.RemoteFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


/**
 * sys文件控制器
 *
 * @author luo.qiang
 * @date 2022/10/16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/file")
@Api(value = "file", tags = "文件管理")
public class SysFileController {

	private final ISysFileService ISysFileService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysFile 文件管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getSysFilePage(Page page, SysFile sysFile) {
		return R.ok(ISysFileService.page(page, Wrappers.query(sysFile)));
	}

	/**
	 * 通过id删除文件管理
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除文件管理", notes = "通过id删除文件管理")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Long id) {
		return R.ok(ISysFileService.deleteFile(id));
	}

	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@ApiOperation(value = "上传文件", notes = "上传文件")
	@PostMapping(value = "/upload")
	public R<RemoteFileVo> upload(@RequestPart("file") MultipartFile file) {
		return ISysFileService.uploadFile(file);
	}

	/**
	 * 获取文件
	 * @param bucket 桶名称
	 * @param fileName 文件空间/名称
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "获取文件", notes = "获取文件")
	@GetMapping("/{bucket}/{fileName}")
	public void file(@PathVariable String bucket, @PathVariable String fileName, HttpServletResponse response) {
		ISysFileService.getFile(bucket, fileName, response);
	}

	/**
	 * 获取本地（resources）文件
	 * @param fileName 文件名称
	 * @param response 本地文件
	 */
	@SneakyThrows
	@GetMapping("/local/file/{fileName}")
	public void localFile(@PathVariable String fileName, HttpServletResponse response) {
		ClassPathResource resource = new ClassPathResource("file/" + fileName);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IoUtil.copy(resource.getInputStream(), response.getOutputStream());
	}

}
