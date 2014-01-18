/*
 * Copyright (c) 2009 OrangeSignal.com All rights reserved.
 * 
 * これは Apache ライセンス Version 2.0 (以下、このライセンスと記述) に
 * 従っています。このライセンスに準拠する場合以外、このファイルを使用
 * してはなりません。このライセンスのコピーは以下から入手できます。
 * 
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 * 
 * 適用可能な法律がある、あるいは文書によって明記されている場合を除き、
 * このライセンスの下で配布されているソフトウェアは、明示的であるか暗黙の
 * うちであるかを問わず、「保証やあらゆる種類の条件を含んでおらず」、
 * 「あるがまま」の状態で提供されるものとします。
 * このライセンスが適用される特定の許諾と制限については、このライセンス
 * を参照してください。
 */

package com.orangesignal.csv.filters;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.orangesignal.csv.filters.ColumnNameInExpression;

/**
 * {@link ColumnNameInExpression} クラスの単体テストです。
 * 
 * @author 杉澤 浩二
 */
public class ColumnNameInExpressionTest {

	@Test(expected = IllegalArgumentException.class)
	public void testColumnNameInExpressionIllegalArgumentException2() {
		final String[] criterias = null;
		new ColumnNameInExpression("col", criterias);
	}

	@Test
	public void testAccept() {
		final List<String> header = Arrays.asList(new String[]{ "col0", "col1", "col2", "col3", "col4" });
		final List<String> values = Arrays.asList(new String[]{ null, "aaa", "bbb", "ccc", "ddd" });
		assertFalse(new ColumnNameInExpression("col0", "a", "aa", "aaa").accept(header, values));
		assertTrue(new ColumnNameInExpression("col1", "a", "aa", "aaa").accept(header, values));
		assertFalse(new ColumnNameInExpression("col2", "a", "aa", "aaa").accept(header, values));
		assertFalse(new ColumnNameInExpression("col0", new String[]{ "a", "aa", "aaa" }, false).accept(header, values));
		assertTrue(new ColumnNameInExpression("col1", new String[]{ "a", "aa", "aaa" }, false).accept(header, values));
		assertFalse(new ColumnNameInExpression("col2", new String[]{ "a", "aa", "aaa" }, false).accept(header, values));
		assertFalse(new ColumnNameInExpression("col0", new String[]{ "A", "AA", "AAA" }, true).accept(header, values));
		assertTrue(new ColumnNameInExpression("col1", new String[]{ "A", "AA", "AAA" }, true).accept(header, values));
		assertFalse(new ColumnNameInExpression("col2", new String[]{ "A", "AA", "AAA" }, true).accept(header, values));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAcceptIllegalArgumentException() {
		new ColumnNameInExpression("col", "a", "aa", "aaa").accept(Arrays.asList(new String[]{ "col0", "col1", "col2" }), null);
	}

	@Test
	public void testToString() {
		assertThat(new ColumnNameInExpression("col", "a", "aa", "aaa").toString(), is("ColumnNameInExpression"));
		
	}

}