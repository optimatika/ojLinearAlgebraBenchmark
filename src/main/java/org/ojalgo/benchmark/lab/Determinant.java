/*
 * Copyright 1997-2019 Optimatika (www.optimatika.se)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.ojalgo.benchmark.lab;

import org.ojalgo.benchmark.BenchmarkRequirementsException;
import org.ojalgo.benchmark.BenchmarkSuite;
import org.ojalgo.benchmark.MatrixBenchmarkLibrary;
import org.ojalgo.benchmark.MatrixBenchmarkOperation;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.RunnerException;

/**
 * <pre>
# Run complete. Total time: 00:15:30

Benchmark            (dim)   (lib)   Mode  Cnt         Score        Error    Units
Determinant.execute     10     ACM  thrpt    3  25782802.727 ±  64675.877  ops/min
Determinant.execute     10    EJML  thrpt    3  27278386.525 ± 584668.666  ops/min
Determinant.execute     10  ojAlgo  thrpt    3  33458646.538 ±  26591.136  ops/min
Determinant.execute    100     ACM  thrpt    3     83695.835 ±    305.917  ops/min
Determinant.execute    100    EJML  thrpt    3    111320.928 ±    291.818  ops/min
Determinant.execute    100  ojAlgo  thrpt    3    128324.306 ±  22547.848  ops/min
Determinant.execute   1000     ACM  thrpt    3        15.473 ±      1.013  ops/min
Determinant.execute   1000    EJML  thrpt    3       132.956 ±      0.718  ops/min
Determinant.execute   1000  ojAlgo  thrpt    3       220.580 ±      2.028  ops/min
 * </pre>
 *
 * @author apete
 */
@State(Scope.Benchmark)
public class Determinant extends MatrixBenchmarkOperation implements BenchmarkSuite.JavaMatrixBenchmark {

    public static void main(final String[] args) throws RunnerException {
        MatrixBenchmarkOperation.run(Determinant.class);
    }

    @Param({ "100", "150", "200", "350", "500", "750", "1000" })
    public int dim;

    @Param({ "ACM", "EJML", "ojAlgo", "MTJ" })
    public String lib;

    private PropertyOperation<?, ?> myOperation;

    Object original;
    Object result;

    @Override
    @Benchmark
    public Object execute() {
        return myOperation.execute(original);
    }

    @Override
    @Setup
    public void setup() {

        library = MatrixBenchmarkLibrary.LIBRARIES.get(lib);

        myOperation = library.getOperationDeterminant(dim);

        original = this.makeRandom(dim, dim, library);
        result = 0.0D;
    }

    @Override
    @TearDown(Level.Iteration)
    public void verify() throws BenchmarkRequirementsException {

        // this.verifyStateless(myOperation.getClass());

    }

}
