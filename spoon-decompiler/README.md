# Spoon-Decompiler

Spoon-decompiler is an extension of spoon that allows working on bytecode by decompiling it (using your favorite decompiler, or one of those already included).
This can be done offline with the help of [JarLauncher](http://spoon.gforge.inria.fr/launcher.html) or DecompiledResource, as well as at loadtime in an agent or a classLoader with the help of [SpoonClassFileTransformer](http://spoon.gforge.inria.fr/agent.html).

This module is released as a separate artifact. To use it with Maven, please add the following lines to your pom:

```xml
<!--latest stable release-->
<dependency>
    <groupId>fr.inria.gforge.spoon</groupId>
    <artifactId>spoon-decompiler</artifactId>
    <version>0.1.0</version>
</dependency>
```

