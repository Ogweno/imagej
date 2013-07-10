/*
 * #%L
 * ImageJ software for multidimensional image processing and analysis.
 * %%
 * Copyright (C) 2009 - 2013 Board of Regents of the University of
 * Wisconsin-Madison, Broad Institute of MIT and Harvard, and Max Planck
 * Institute of Molecular Cell Biology and Genetics.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

package imagej.data.types;

import java.math.BigDecimal;

import net.imglib2.type.numeric.integer.UnsignedShortType;

import org.scijava.AbstractContextual;
import org.scijava.plugin.Plugin;

/**
 * @author Barry DeZonia
 */
@Plugin(type = DataType.class)
public class DataType16BitUnsignedInteger extends AbstractContextual implements
	DataType<UnsignedShortType>
{

	private final UnsignedShortType type = new UnsignedShortType();

	@Override
	public UnsignedShortType getType() {
		return type;
	}

	@Override
	public String name() {
		return "16-bit unsigned integer";
	}

	@Override
	public String description() {
		return "An integer data type ranging between 0 and 65535";
	}

	@Override
	public boolean isFloat() {
		return false;
	}

	@Override
	public boolean isSigned() {
		return false;
	}

	@Override
	public boolean isBoundedFully() {
		return true;
	}

	@Override
	public boolean isBoundedBelow() {
		return true;
	}

	@Override
	public boolean isBoundedAbove() {
		return true;
	}

	@Override
	public void lowerBound(UnsignedShortType dest) {
		dest.set(0);
	}

	@Override
	public void upperBound(UnsignedShortType dest) {
		dest.set(0xffff);
	}

	@Override
	public int bitCount() {
		return 16;
	}

	@Override
	public UnsignedShortType createVariable() {
		return new UnsignedShortType();
	}

	@Override
	public BigDecimal asBigDecimal(UnsignedShortType val) {
		return BigDecimal.valueOf(val.get());
	}

	@Override
	public void cast(long val, UnsignedShortType dest) {
		if (val < 0) dest.set(0);
		else if (val > 0xffff) dest.set(0xffff);
		else dest.set((int) (val & 0xffff));
	}

	@Override
	public void cast(double val, UnsignedShortType dest) {
		cast((long) val, dest);
	}

	@Override
	public void cast(BigDecimal val, UnsignedShortType dest) {
		cast(val.longValue(), dest);
	}

}