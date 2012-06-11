JPGF Library
============

This library aims at making possible to use PGF files, produce by the grammatical framework gf compiler in java program. On of the goal is to build application for android devices.

Dependencies
------------

In order to build the library you need a java compiler, a scala compiler and the ant build tool.

Compilation
-----------

The project now uses ant for the build process. The following commands can be interesting : 

Building the library :
> ant build

Testing the library
> ant test

Building a standalone jar file :
> ant jar
This builds the all library as a single jar file and includes external dependencies (cup, guava...)


More information
----------------

The web page of the project : www.grammaticalframework.org/android/
you can also write me an email : gdetrez@crans.org


Open source licences
--------------------

guava.jar

```
/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
```

java_cup.jar

```
CUP Parser Generator Copyright Notice, License, and Disclaimer

Copyright 1996-1999 by Scott Hudson, Frank Flannery, C. Scott Ananian
Permission to use, copy, modify, and distribute this software and its
documentation for any purpose and without fee is hereby granted,
provided that the above copyright notice appear in all copies and that
both the copyright notice and this permission notice and warranty
disclaimer appear in supporting documentation, and that the names of
the authors or their employers not be used in advertising or publicity
pertaining to distribution of the software without specific, written
prior permission.

The authors and their employers disclaim all warranties with regard to
this software, including all implied warranties of merchantability and
fitness. In no event shall the authors or their employers be liable
for any special, indirect or consequential damages or any damages
whatsoever resulting from loss of use, data or profits, whether in an
action of contract, negligence or other tortious action, arising out
of or in connection with the use or performance of this software.

This is an open source license. It is also GPL-Compatible (see entry
for "Standard ML of New Jersey"). The portions of CUP output which are
hard-coded into the CUP source code are (naturally) covered by this
same license, as is the CUP runtime code linked with the generated
parser.
Java is a trademark of Sun Microsystems, Inc. References to the Java
programming language in relation to JLex are not meant to imply that
Sun endorses this product.
```
