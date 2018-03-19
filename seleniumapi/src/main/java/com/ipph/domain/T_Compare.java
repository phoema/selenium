package com.ipph.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 客户端IP黑名单
 * @author jiahh
 *
 */
@Entity
@Table(name = "T_Compare")
@Data // lombock
public class T_Compare implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String sxtitle;
	@Column(length=2000)
	private String sxcontent;
	//机构编码
	private String bkname;
	// 
	private String bktitle;
	private String bksection;
	@Column(length=2000)
	private String bkcontent;
	// 代理量
	private String bklink;
	//负责人
	private Double likevalue;

	

	
}
