//=======================================================================
// BasicALU for chisel3
// watz0n
// Jan. 31, 2018
// https://github.com/librecores/riscv-sodor/wiki/Tutorial-page-3#32-the-chisel-uint-class
//=======================================================================
package basicalu

import chisel3._

class BasicALU extends Module { 
  //val io = new Bundle {           //Need IO() class for Chisel3
  val io = IO(new Bundle {
    //val a      = UInt(INPUT, 64)  //Seems Chisel 2.X syntax, change to Chisel3 syntax
    //val b      = UInt(INPUT, 64)  //Seems Chisel 2.X syntax, change to Chisel3 syntax
    //val opcode = UInt(INPUT, 4)   //Seems Chisel 2.X syntax, change to Chisel3 syntax
    //val output = UInt(OUTPUT, 4)  //Seems Chisel 2.X syntax, change to Chisel3 syntax
    val a      = Input(UInt(64.W))
    val b      = Input(UInt(64.W))
    val opcode = Input(UInt(4.W))
    val output = Output(UInt(64.W))
  })
  
  io.output := UInt(0)
  when (io.opcode === 0.U) { 
    io.output := io.a                   // pass A 
  } .elsewhen (io.opcode === UInt(1)) { 
    io.output := io.b                   // pass B 
  } .elsewhen (io.opcode === UInt(2)) { 
    io.output := io.a + UInt(1)         // inc A by 1 
  } .elsewhen (io.opcode === UInt(3)) { 
    io.output := io.a - UInt(1)         // inc B by 1 
  } .elsewhen (io.opcode === UInt(4)) { 
    io.output := io.a + UInt(4)         // inc A by 4 
  } .elsewhen (io.opcode === UInt(5)) { 
    io.output := io.a - UInt(4)         // dec A by 4 
  } .elsewhen (io.opcode === UInt(6)) { 
    io.output := io.a + io.b            // add A and B 
  } .elsewhen (io.opcode === UInt(7)) { 
    io.output := io.a - io.b            // sub B from A 
  } .elsewhen (io.opcode === UInt(8)) { 
    io.output := (io.a < io.b)          // set on A < B 
  } .otherwise { 
    io.output := (io.a === io.b)        // set on A == B 
  }

}