/**
 * Copyright (C) 2006-2018 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.test.compilationunit;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.reflect.declaration.CtCompilationUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link CtCompilationUnit#getBinaryFiles()}.
 */
public class GetBinaryFilesTest {

	@Test
	public void testSingleBinary(@TempDir Path tmpFolder) {
		final String input = "./src/test/resources/compilation/compilation-tests/IBar.java";
		final Launcher launcher = new Launcher();
		launcher.addInputResource(input);
		launcher.setBinaryOutputDirectory(tmpFolder.toString());
		launcher.buildModel();
		launcher.getModelBuilder().compile(SpoonModelBuilder.InputType.FILES);

		final Map<String, CtCompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
		assertEquals(1, cus.size());

		final List<File> binaries = cus.values().iterator().next().getBinaryFiles();
		assertEquals(1, binaries.size());
		assertEquals("IBar.class", binaries.get(0).getName());
		assertTrue(binaries.get(0).isFile());
	}

	@Test
	public void testExistingButNotBuiltBinary(@TempDir Path tmpFolder) throws IOException {
		new File(tmpFolder.toFile(), "compilation").mkdir();
		new File(tmpFolder.toFile(), "compilation/IBar$Test.class").createNewFile();

		final String input = "./src/test/resources/compilation/compilation-tests/IBar.java";
		final Launcher launcher = new Launcher();
		launcher.addInputResource(input);
		launcher.setBinaryOutputDirectory(tmpFolder.toString());
		launcher.buildModel();
		launcher.getModelBuilder().compile(SpoonModelBuilder.InputType.FILES);

		final Map<String, CtCompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
		assertEquals(1, cus.size());

		final List<File> binaries = cus.values().iterator().next().getBinaryFiles();
		assertEquals(1, binaries.size());
		assertEquals("IBar.class", binaries.get(0).getName());
		assertTrue(binaries.get(0).isFile());

		final File[] files = binaries.get(0).getParentFile().listFiles();
		assertNotNull(files);
		assertEquals(2, files.length);
		assertTrue("IBar$Test.class".equals(files[0].getName()) || "IBar$Test.class".equals(files[1].getName()));
	}

	@Test
	public void testMultiClassInSingleFile(@TempDir Path tmpFolder) throws IOException {
		final String input = "./src/test/resources/compilation/compilation-tests/";
		final Launcher launcher = new Launcher();
		launcher.addInputResource(input);
		launcher.setBinaryOutputDirectory(tmpFolder.toString());
		launcher.buildModel();
		launcher.getModelBuilder().compile(SpoonModelBuilder.InputType.FILES);

		final Map<String, CtCompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
		assertEquals(2, cus.size());

		final List<File> ibarBinaries = cus.get(new File(input, "IBar.java").getCanonicalFile().getAbsolutePath()).getBinaryFiles();
		assertEquals(1, ibarBinaries.size());
		assertEquals("IBar.class", ibarBinaries.get(0).getName());
		assertTrue(ibarBinaries.get(0).isFile());

		final List<File> barBinaries = cus.get(new File(input, "Bar.java").getCanonicalFile().getAbsolutePath()).getBinaryFiles();
		assertEquals(2, barBinaries.size());
		assertEquals("Bar.class", barBinaries.get(0).getName());
		assertEquals("FooEx.class", barBinaries.get(1).getName());
		assertTrue(barBinaries.get(0).isFile());
		assertTrue(barBinaries.get(1).isFile());
	}

	@Test
	public void testNestedTypes(@TempDir Path tmpFolder) throws IOException {
		final String input = "./src/test/java/spoon/test/imports/testclasses/internal/PublicInterface2.java";
		final Launcher launcher = new Launcher();
		launcher.addInputResource(input);
		launcher.setBinaryOutputDirectory(tmpFolder.toString());
		launcher.buildModel();
		launcher.getModelBuilder().compile(SpoonModelBuilder.InputType.FILES);

		final Map<String, CtCompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
		assertEquals(1, cus.size());

		final List<File> binaries = cus.get(new File(input).getCanonicalFile().getAbsolutePath()).getBinaryFiles();
		assertEquals(3, binaries.size());
		assertEquals("PublicInterface2.class", binaries.get(0).getName());
		assertEquals("PublicInterface2$NestedInterface.class", binaries.get(1).getName());
		assertEquals("PublicInterface2$NestedClass.class", binaries.get(2).getName());
		assertTrue(binaries.get(0).isFile());
		assertTrue(binaries.get(1).isFile());
		assertTrue(binaries.get(2).isFile());
	}

	@Test
	public void testAnonymousClasses(@TempDir Path tmpFolder) throws IOException {
		final String input = "./src/test/java/spoon/test/secondaryclasses/testclasses/AnonymousClass.java";
		final Launcher launcher = new Launcher();
		launcher.addInputResource(input);
		launcher.setBinaryOutputDirectory(tmpFolder.toString());
		launcher.buildModel();
		launcher.getModelBuilder().compile(SpoonModelBuilder.InputType.FILES);

		final Map<String, CtCompilationUnit> cus = launcher.getFactory().CompilationUnit().getMap();
		assertEquals(1, cus.size());

		final List<File> binaries = cus.get(new File(input).getCanonicalFile().getAbsolutePath()).getBinaryFiles();
		assertEquals(4, binaries.size());
		assertEquals("AnonymousClass.class", binaries.get(0).getName());
		assertEquals("AnonymousClass$I.class", binaries.get(1).getName());
		assertEquals("AnonymousClass$1.class", binaries.get(2).getName());
		assertEquals("AnonymousClass$2.class", binaries.get(3).getName());
		assertTrue(binaries.get(0).isFile());
		assertTrue(binaries.get(1).isFile());
		assertTrue(binaries.get(2).isFile());
		assertTrue(binaries.get(3).isFile());
	}
}
