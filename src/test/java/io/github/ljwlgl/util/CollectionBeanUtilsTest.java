package io.github.ljwlgl.util;

import com.google.common.collect.Lists;
import io.github.ljwlgl.vo.UserVO;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * @author liwei
 * @date 2020/7/5 4:44 下午
 */
public class CollectionBeanUtilsTest {

  @Test
  public void test1() {
    List<UserVO> userList = Lists
        .newArrayList(UserVO.builder()
            .sex(1)
            .name("liwei")
            .school("华中科技大学")
            .build(), UserVO.builder()
            .sex(2)
            .name("shaowenting")
            .school("湖北汽车工业学院")
            .build(),UserVO.builder()
            .sex(1)
            .name("zhangxinxin")
            .school("华中科技大学")
            .build());
    Map<String, List<UserVO>> result = CollectionBeanUtils.groupToMap(userList, UserVO.class, "name");
    System.out.println(result);
  }

}
