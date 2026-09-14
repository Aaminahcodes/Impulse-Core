import chisel3._
import chisel3.util._
import ALUOP._

class ALUIO extends Bundle with Config {
  val in_A  = Input(SInt(WLEN.W))
  val in_B  = Input(SInt(WLEN.W))
  val aluop = Input(SInt(ALUOP_SIG_LEN.W))
  val output = Output(SInt(WLEN.W))
}

class aluOP extends Module with Config {
  val io = IO(new ALUIO)

  io.output := 0.S

  when(io.aluop === ALU_ADD) {
    io.output := io.in_A + io.in_B
  }.elsewhen(io.aluop === ALU_SUB) {
    io.output := io.in_A - io.in_B
  }.elsewhen(io.aluop === ALU_AND) {
    io.output := io.in_A & io.in_B
  }.elsewhen(io.aluop === ALU_OR) {
    io.output := io.in_A | io.in_B
  }.elsewhen(io.aluop === ALU_XOR) {
    io.output := io.in_A ^ io.in_B
  }.elsewhen(io.aluop === ALU_SLT) {
    io.output := (io.in_A < io.in_B).asSInt
  }.elsewhen(io.aluop === ALU_SLL) {
    io.output := (io.in_A.asUInt << io.in_B(4, 0).asUInt).asSInt
  }.elsewhen(io.aluop === ALU_SLTU) {
    io.output := (io.in_A.asUInt < io.in_B.asUInt).asSInt
  }.elsewhen(io.aluop === ALU_SRL) {
    io.output := (io.in_A.asUInt >> io.in_B(4, 0).asUInt).asSInt
  }.elsewhen(io.aluop === ALU_SRA) {
    io.output := io.in_A >> io.in_B(4, 0)
  }.elsewhen(io.aluop === ALU_MUL) {
    io.output := io.in_A * io.in_B
  }.elsewhen(io.aluop === ALU_DIV) {
    when(io.in_B === 0.S) {
      io.output := (-1).S
    }.otherwise {
      io.output := io.in_A / io.in_B
    }
  }.otherwise {
    io.output := 0.S
  }
}