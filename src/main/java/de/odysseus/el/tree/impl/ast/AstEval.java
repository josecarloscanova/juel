/*
 * Copyright 2006-2009 Odysseus Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.odysseus.el.tree.impl.ast;

import javax.el.ELContext;
import javax.el.MethodInfo;
import javax.el.ValueReference;

import de.odysseus.el.tree.Bindings;

public final class AstEval extends AstNode {
	private final AstNode child;
	private final boolean deferred;

	public AstEval(AstNode child, boolean deferred) {
		this.child = child;
		this.deferred = deferred;
	}

	public boolean isDeferred() {
		return deferred;
	}

	public boolean isLeftValue() {
		return getChild(0).isLeftValue();
	}

	public ValueReference getValueReference(Bindings bindings, ELContext context) {
		return child.getValueReference(bindings, context);
	}
	
	@Override
	public Object eval(Bindings bindings, ELContext context) {
		return child.eval(bindings, context);
	}

	@Override
	public String toString() {
		return (deferred ? "#" : "$") +"{...}";
	}	

	@Override
	public void appendStructure(StringBuilder b, Bindings bindings) {
		b.append(deferred ? "#{" : "${");
		child.appendStructure(b, bindings);
		b.append("}");
	}

	public MethodInfo getMethodInfo(Bindings bindings, ELContext context, Class<?> returnType, Class<?>[] paramTypes) {
		return child.getMethodInfo(bindings, context, returnType, paramTypes);
	}

	public Object invoke(Bindings bindings, ELContext context, Class<?> returnType, Class<?>[] paramTypes, Object[] paramValues) {
		return child.invoke(bindings, context, returnType, paramTypes, paramValues);
	}

	public Class<?> getType(Bindings bindings, ELContext context) {
		return child.getType(bindings, context);
	}

	public boolean isLiteralText() {
		return child.isLiteralText();
	}

	public boolean isReadOnly(Bindings bindings, ELContext context) {
		return child.isReadOnly(bindings, context);
	}

	public void setValue(Bindings bindings, ELContext context, Object value) {
		child.setValue(bindings, context, value);
	}

	public int getCardinality() {
		return 1;
	}

	public AstNode getChild(int i) {
		return i == 0 ? child : null;
	}
}
