package com.joeseff;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Joeseff
 * @created 16/05/2020 16:45
 */
public class NullRemovalTest {

	@Test
	public void givenListContainsNullsWhenRemovingNullsWithPlainJavaPlainThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, null);
		while(list.remove(null));

		assertThat(list, hasSize(1));
	}

	@Test
	public void givenListContainsNullsWhenRemovingNullsWithPlainJavaAlternativeThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, null);
		list.removeAll(Collections.singleton(null));

		assertThat(list, hasSize(1));
	}

	@Test
	public void givenListContainsNullsWhenRemovingNullsWithGuavaV1ThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, null);
		Iterables.removeIf(list, Predicates.isNull());

		assertThat(list, hasSize(1));
	}

	@Test
	public void givenListContainsNullsWhenRemovingNullsWithGuavaV2ThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, null, 2, 3);
		List<Integer> listWithoutNulls = Lists.newArrayList(
				Iterables.filter(list, Predicates.notNull())
		);

		assertThat(listWithoutNulls, hasSize(3));
	}

	@Test
	public void givenListContainsNullsWhenRemovingNullsWithCOmmonsCollectionsThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
		CollectionUtils.filter(list, PredicateUtils.notNullPredicate());

		assertThat(list, hasSize(3));
	}

	@Test
	public void givenListContainsNullsWhenFilteringParallelThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
		List<Integer> listWithoutNulls = list.parallelStream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		assertThat(listWithoutNulls, hasSize(3));
	}

	@Test
	public void givenListCOntainsNullsWhenFilteringSerialThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
		List<Integer> listWithoutNulls = list.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		assertThat(listWithoutNulls, hasSize(3));
	}

	@Test
	public void givenListContainsNullsWhenRemovingNullsWithRemoveIfThenCorrect() {
		List<Integer> list = Lists.newArrayList(null, 1, 2, null, 3, null);
		list.removeIf(Objects::isNull);

		assertThat(list, hasSize(3));
	}
}
