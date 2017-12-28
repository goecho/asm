// ASM: a very small and fast Java bytecode manipulation framework
// Copyright (c) 2000-2011 INRIA, France Telecom
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
// 3. Neither the name of the copyright holders nor the names of its
//    contributors may be used to endorse or promote products derived from
//    this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
// THE POSSIBILITY OF SUCH DAMAGE.
package org.objectweb.asm.benchmarks;

import java.io.ByteArrayInputStream;

import serp.bytecode.BCClass;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.Project;

/**
 * An {@link Adapter} implemented with the SERP library.
 *
 * @author Eric Bruneton
 */
public class SERPAdapter extends Adapter {

  @Override
  public byte[] readAndWrite(final byte[] classFile, final boolean computeMaxs) {
    BCClass bcClass = new Project().loadClass(new ByteArrayInputStream(classFile));
    bcClass.getDeclaredFields();
    BCMethod[] bcMethods = bcClass.getDeclaredMethods();
    for (int i = 0; i < bcMethods.length; ++i) {
      Code code = bcMethods[i].getCode(false);
      if (code != null) {
        while (code.hasNext()) {
          code.next();
        }
        if (computeMaxs) {
          code.calculateMaxStack();
          code.calculateMaxLocals();
        }
      }
    }
    return bcClass.toByteArray();
  }
}
