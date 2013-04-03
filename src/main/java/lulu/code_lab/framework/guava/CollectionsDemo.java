package lulu.code_lab.framework.guava;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CollectionsDemo {

	@Test
	public void init() {
		//初始化List
		List<String> list = Lists.newArrayList();
		List<String> list2 = Lists.newArrayList("a", "b", "c", "d");
		List<String> list3 = Lists.newArrayList();
		//初始化Map
		Map<String, String> map = Maps.newHashMap();
		Map<Integer, String> unmodifiedMap = ImmutableMap.of();
	}
}
