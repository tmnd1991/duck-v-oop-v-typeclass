# Test virtual dispatch vs typeclass vs duck typing in Scala

```
jmh:run -i 20 -wi 20 -f2 -t2
```

The result was on a 2017 15' Macbook Pro (azul jdk 8)

```
[info] # Run complete. Total time: 00:04:15
[info] Benchmark                 Mode  Cnt   Score   Error  Units
[info] TestBenchmark.duck       thrpt   40  45.067 ± 2.296  ops/s
[info] TestBenchmark.oop        thrpt   40  51.407 ± 1.035  ops/s
[info] TestBenchmark.typeclass  thrpt   40  48.288 ± 1.094  ops/s
```