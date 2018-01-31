//=======================================================================
// BasicALU Unit-Test Framework
// watz0n
// Jan. 31, 2018
// Note: A simple implement to setup large number (64-bit) test case
//=======================================================================
package basicalu

import chisel3._
import chisel3.iotesters._
import org.scalatest._ 
import org.scalatest.exceptions._  

class BasicALUPeekPokeTester(dut: BasicALU, a:UInt, b:UInt, op:UInt, c:UInt) extends PeekPokeTester(dut)  {

  poke(dut.io.a, a)
  poke(dut.io.b, b)
  poke(dut.io.opcode, op)
  step(1)
  expect(dut.io.output, c)
  step(1)
  
  //Use peek function in PeekPokeTester
  //Ref: https://github.com/freechipsproject/chisel-testers/wiki/Using-the-PeekPokeTester#the-test-harness
  if(peek(dut.io.output) == c.litValue()) {
    println("Result as expect.")
  }
  else {
    println("Something error.")
  }

}

class BasicALUPeekPokeSpec extends ChiselFlatSpec with Matchers {

  it should "Test1: BasicALU functional test" in {
    
    val manager = new TesterOptionsManager {
      testerOptions = testerOptions.copy(backendName = "verilator")
    }

    var test_count = 0
    val alu_tests = List(
    ("h0000000000000001".U, "h0000000000000001".U, UInt(6, 4), "h0000000000000002".U))

    alu_tests.foreach { tupleElement => {
        val (a:UInt, b:UInt, op:UInt, c:UInt) = tupleElement
        chisel3.iotesters.Driver.execute(() => new BasicALU, manager) {
            dut => new BasicALUPeekPokeTester(dut, a, b, op, c)
        } should be (true)
    }}
  }

}