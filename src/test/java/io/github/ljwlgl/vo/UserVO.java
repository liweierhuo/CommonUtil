package io.github.ljwlgl.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liwei
 * @date 2020/7/5 4:46 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

  private Integer sex;

  private String name;

  private String school;

  private Integer age;
}
