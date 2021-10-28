# Test virtual dispatch vs typeclass vs duck typing in Scala

```
jmh:run -i 20 -wi 20 -f2 -t2
```

The result was on a 2017 15' Macbook Pro (azul jdk 8)

```
[info] # Run complete. Total time: 00:04:14
[info] Benchmark                 Mode  Cnt   Score   Error  Units
[info] TestBenchmark.duck       thrpt   40  44.016 ± 2.255  ops/s
[info] TestBenchmark.oop        thrpt   40  49.451 ± 2.705  ops/s
[info] TestBenchmark.typeclass  thrpt   40  53.885 ± 2.337  ops/s
```