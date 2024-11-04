package com.example.exceptiontt.key2.entity;

import lombok.Data;

@Data
public class License {
//   private String license;// 许可表
   private String machine_code;// 机器码（必须）
   private String customer_name;// 客户名称
   private String contact_name ;//联系人姓名
   private String contact_tel;//	联系人电话号码
   private String contact_email	;//联系人邮箱地址
   private String start_date;// 开始时间（必须）
   private String end_date;//	终了时间（必须）
   private String users_count;//	用户数量（必须）
   private String connections;// 连接数量（必须）
   private String license_kind;//	许可类型：01：普通版/02：专业版	（必须）
   private String file_location;//: 文件地址：存放license文件的地址
   private String build_date;// 生成时间
   private String function_mapping ;//功能映射

//   @Override
//   public String toString(){
//      return machine_code+customer_name+contact_name
//   }
}
