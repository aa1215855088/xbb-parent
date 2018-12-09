package com.hnguigu.xbb.user.domian;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * bbs_buyer实体类
 * 
 * @author 
 *
 */
@TableName("bbs_buyer")
public class User implements Serializable {

    private static final long serialVersionUID = 711741367708225473L;
    /**用户名*/
	private String username; 
	/**密码*/
	private String password; 
	/***/
	private String gender; 
	/**邮箱*/
	private String email; 
	/**真实名字*/
	private String realName; 
	/**注册时间*/
	private Date registerTime;
	/**省ID*/
	private String province; 
	/**市ID*/
	private String city; 
	/**县ID*/
	private String town; 
	/**地址*/
	private String addr; 
	/**是否已删除:1:未,0:删除了*/
	private Integer isDel;
	/**头像*/
	private String image;
	/**
	 * 实例化
	 */
	public User() {
		super();
	}

	
	/**
	 * 将当前对象转换为JsonObject
	 * 
	 * @return
	 */
	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		if (this.getUsername() != null) {
			result.put("username",this.getUsername());
		}
		if (this.getPassword() != null) {
			result.put("password",this.getPassword());
		}
		if (this.getGender() != null) {
			result.put("gender",this.getGender());
		}
		if (this.getEmail() != null) {
			result.put("email",this.getEmail());
		}
		if (this.getRealName() != null) {
			result.put("realName",this.getRealName());
		}
		if (this.getRegisterTime() != null) {
			result.put("registerTime",this.getRegisterTime());
		}
		if (this.getProvince() != null) {
			result.put("province",this.getProvince());
		}
		if (this.getCity() != null) {
			result.put("city",this.getCity());
		}
		if (this.getTown() != null) {
			result.put("town",this.getTown());
		}
		if (this.getAddr() != null) {
			result.put("addr",this.getAddr());
		}
		if (this.getIsDel() != null) {
			result.put("isDel",this.getIsDel());
		}
		if(this.getImage()!=null){
		    result.put("image",this.getImage());
        }
		return result;
	}


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
	 * 获取username
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置username
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 获取password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取gender
	 * 
	 * @return
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * 设置gender
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * 获取email
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 获取realName
	 * 
	 * @return
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 设置realName
	 * 
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
	 * 获取province
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 设置province
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	
	/**
	 * 获取city
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 设置city
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 获取town
	 * 
	 * @return
	 */
	public String getTown() {
		return town;
	}

	/**
	 * 设置town
	 * 
	 * @param town
	 */
	public void setTown(String town) {
		this.town = town;
	}
	
	/**
	 * 获取addr
	 * 
	 * @return
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * 设置addr
	 * 
	 * @param addr
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	/**
	 * 获取isDel
	 * 
	 * @return
	 */
	public Integer getIsDel() {
		return isDel;
	}

	/**
	 * 设置isDel
	 * 
	 * @param isDel
	 */
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public String toString() {
		return "User [username=" + username + " , password=" + password + " , gender=" + gender + " , email=" + email + " , realName=" + realName + " , registerTime=" + registerTime + " , province=" + province + " , city=" + city + " , town=" + town + " , addr=" + addr + " , isDel=" + isDel + "  ]";
	
	}
	
	
}
