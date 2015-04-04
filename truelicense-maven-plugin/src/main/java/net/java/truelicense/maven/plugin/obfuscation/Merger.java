/*
 * Copyright (C) 2005-2015 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package net.java.truelicense.maven.plugin.obfuscation;

import javax.annotation.CheckForNull;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.*;

/**
 * Merges {@code <clinit>} methods.
 * This class visitor requires the flag
 * {@link org.objectweb.asm.ClassWriter#COMPUTE_MAXS} to be set.
 *
 * @author Christian Schlichtherle
 */
final class Merger extends Visitor {

    private final String prefix;
    private @CheckForNull O9nInitMethodVisitor imv;
    private int counter;

    Merger(final Processor ctx, final String prefix, final ClassVisitor cv) {
        super(ctx, cv);
        this.prefix = prefix;
    }

    @Override public @CheckForNull MethodVisitor visitMethod(
            final int access,
            final String name,
            final String desc,
            final @CheckForNull String signature,
            final @CheckForNull String[] exceptions) {
        MethodVisitor mv;
        if ("<clinit>".equals(name)) {
            if (isInterface()) {
                mv = imv;
                if (null == mv) {
                    mv = cv.visitMethod(access, name, desc, signature, exceptions);
                    if (null != mv)
                        mv = imv = new O9nInterfaceInitMethodVisitor(mv);
                }
            } else {
                final String n = methodName(prefix, counter++);
                mv = cv.visitMethod(ACC_PRIVATE | ACC_STATIC | ACC_SYNTHETIC, n, desc, signature, exceptions);
                O9nInitMethodVisitor imv = this.imv;
                if (null == imv) {
                    imv = this.imv = new O9nInitMethodVisitor(
                            cv.visitMethod(access, name, desc, signature, exceptions));
                    imv.visitCode();
                }
                imv.visitMethodInsn(INVOKESTATIC, internalClassName(), n, desc, false);
            }
        } else {
            mv = cv.visitMethod(access, name, desc, signature, exceptions);
        }
        return mv;
    }

    @Override public void visitEnd() {
        final O9nInitMethodVisitor imv = this.imv;
        if (null != imv) imv.visitInitEnd();
        cv.visitEnd();
    }

    private static class O9nInitMethodVisitor extends MethodVisitor {

        O9nInitMethodVisitor(MethodVisitor mv) { super(ASM4, mv); }

        @Override public final void visitMaxs(int maxStack, int maxLocals) { }

        @Override public final void visitEnd() { }

        final void visitInitEnd() {
            mv.visitInsn(RETURN);
            mv.visitMaxs(0, 0); // let compute
            mv.visitEnd();
        }
    } // O9nInitMethodVisitor

    private static class O9nInterfaceInitMethodVisitor
    extends O9nInitMethodVisitor {

        O9nInterfaceInitMethodVisitor(MethodVisitor mv) { super(mv); }

        @Override public void visitInsn(int opcode) {
            if (RETURN != opcode) mv.visitInsn(opcode);
        }
    } // O9nInterfaceInitMethodVisitor
}
