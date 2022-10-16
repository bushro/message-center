package com.bushro.message.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "上传文件返回对象")
public class RemoteFileVo {

	@ApiModelProperty(value = "文件服务器中的逻辑分区")
	private String bucket;

	@ApiModelProperty(value = "文件服务器中的文件名")
	private String fileName;

	@ApiModelProperty(value = "可以访问资源的url，前端请求完后可以把这个url用于后面的提交")
	private String url;

}
