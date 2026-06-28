/*
 * Copyright 2017 - 2026 Volker Berlin (i-net software)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.inetsoftware.jwebassembly.wasm;

/**
 * Predefined types has a negative value. Will be write as a single byte in the binary output.<br>
 * https://webassembly.github.io/spec/core/binary/types.html
 * 
 * @author Volker Berlin
 */
public enum ValueType implements AnyType {
    i32(-0x01), // 0x7F
    i64(-0x02), // 0x7E
    f32(-0x03), // 0x7D
    f64(-0x04), // 0x7C
    v128(-0x05), // 0x7B
    bool(-0x08),
    i8(-0x08),  // 0x78
    i16(-0x09), // 0x77
    u16(-0x09),
    funcref(-0x10),
    externref(-0x11), //TODO rename to any
    anyref(-0x12), // TODO obsolete
    eqref(-0x13),
    ref(-0x1C), // 0x64
    ref_null(-0x1D), // 0x63
    exnref(-0x18), // https://github.com/WebAssembly/exception-handling/blob/master/proposals/Exceptions.md
    func(-0x20), // 0x60, Composite Type
    struct(-0x21), // 0x5F, Composite Type
    array(-0x22), // 0x5E, Composite Type
    sub( -0x30 ), // 0x50 recursive type: sub
    sub_final( -0x31 ), // 0x4F recursive type: sub final
    rec( -0x32 ), // 0x4E recursive type: list of recursion
    empty(-0x40), // empty/void block_type
    ;

    private final int code;

    /**
     * Create instance of the enum
     * 
     * @param code
     *            the operation code in WebAssembly
     */
    private ValueType( int code ) {
        this.code = code;
    }

    /**
     * The operation code in WebAssembly.
     * 
     * @return the code
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRefType() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSubTypeOf( AnyType type ) {
        if( type == this ) {
            return true;
        }
        switch( this ) {
            case externref:
                return type.isRefType();
            case eqref:
                return type.isRefType() || type == externref;
            default:
                return false;
        }
    }
}
