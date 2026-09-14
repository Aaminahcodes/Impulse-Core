import chisel3._
import chisel3.util._

class alubranchIO extends Bundle {
  val func3    = Input(UInt(3.W))
  val branch   = Input(Bool())
  val data_1   = Input(UInt(32.W))
  val data_2   = Input(UInt(32.W))
  val br_taken = Output(Bool())
}

class alubranch extends Module {
  val io = IO(new alubranchIO)

  io.br_taken := false.B

  when(io.branch) {
    when(io.func3 === "b000".U) {        // BEQ
      io.br_taken := io.data_1 === io.data_2
    }.elsewhen(io.func3 === "b001".U) {  // BNE
      io.br_taken := io.data_1 =/= io.data_2
    }.elsewhen(io.func3 === "b100".U) {  // BLT
      io.br_taken := io.data_1.asSInt < io.data_2.asSInt
    }.elsewhen(io.func3 === "b101".U) {  // BGE
      io.br_taken := io.data_1.asSInt >= io.data_2.asSInt
    }.elsewhen(io.func3 === "b110".U) {  // BLTU
      io.br_taken := io.data_1 < io.data_2
    }.elsewhen(io.func3 === "b111".U) {  // BGEU
      io.br_taken := io.data_1 >= io.data_2
    }
  }
}