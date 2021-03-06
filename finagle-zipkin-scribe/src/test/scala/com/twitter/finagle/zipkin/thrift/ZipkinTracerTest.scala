package com.twitter.finagle.zipkin.thrift

import com.twitter.app.Flags
import com.twitter.finagle.zipkin.core.{DefaultSampler, Sampler}
import com.twitter.finagle.zipkin.initialSampleRate
import org.scalatest.FunSuite

class ZipkinTracerTest extends FunSuite {

  test("sampling uses the static default sampling rate if not overridden") {
    val tracer = new ScribeZipkinTracer()
    assert(tracer.getSampleRate == Sampler.DefaultSampleRate)

    initialSampleRate.reset()
  }

  test("passing sample rate in the ZipkinTracer constructor always takes precedence") {
    val flag = new Flags("foo", true)
    flag.parseArgs(Array("-com.twitter.finagle.zipkin.initialSampleRate", "0.63"))
    DefaultSampler.reset()

    val tracer = new ZipkinTracer(ScribeRawZipkinTracer(), 1.0f)
    assert(tracer.getSampleRate == 1.0f)

    initialSampleRate.reset()
  }

}
