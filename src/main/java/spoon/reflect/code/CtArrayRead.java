/*
 * SPDX-License-Identifier: (MIT OR CECILL-C)
 *
 * Copyright (C) 2006-2023 INRIA and contributors
 *
 * Spoon is available either under the terms of the MIT License (see LICENSE-MIT.txt) or the Cecill-C License (see LICENSE-CECILL-C.txt). You as the user are entitled to choose the terms under which to adopt Spoon.
 */
package spoon.reflect.code;

/**
 * This code element defines a read access to an array.
 *
 * In Java, it is a usage of an array outside an assignment. For example,
 * <pre>
 *     int[] array = new int[10];
 *     System.out.println(
 *     array[0] // &lt;-- array read
 *     );
 * </pre>
 *
 *
 * @param <T>
 * 		type of the array
 */
public interface CtArrayRead<T> extends CtArrayAccess<T, CtExpression<?>> {
	@Override
	CtArrayRead<T> clone();
}
