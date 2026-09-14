import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val opcode   = Input(UInt(7.W))
    val regWrite = Output(Bool())
    val memRead  = Output(Bool())
    val memWrite = Output(Bool())
    val memToReg = Output(Bool())
    val aluSrc   = Output(Bool())
    val branch   = Output(Bool())
    val jal      = Output(Bool())
    val jalr     = Output(Bool())
    val lui      = Output(Bool())
    val auipc    = Output(Bool())
  })

  // Defaults
  io.regWrite := false.B
  io.memRead  := false.B
  io.memWrite := false.B
  io.memToReg := false.B
  io.aluSrc   := false.B
  io.branch   := false.B
  io.jal      := false.B
  io.jalr     := false.B
  io.lui      := false.B
  io.auipc    := false.B

  when(io.opcode === "b0110011".U) {          // R-type
    io.regWrite := true.B
  }.elsewhen(io.opcode === "b0010011".U) {    // I-type ALU
    io.regWrite := true.B
    io.aluSrc   := true.B
  }.elsewhen(io.opcode === "b0000011".U) {    // LW
    io.regWrite := true.B
    io.memRead  := true.B
    io.memToReg := true.B
    io.aluSrc   := true.B
  }.elsewhen(io.opcode === "b0100011".U) {    // SW
    io.memWrite := true.B
    io.aluSrc   := true.B
  }.elsewhen(io.opcode === "b1100011".U) {    // Branch
    io.branch   := true.B
  }.elsewhen(io.opcode === "b1101111".U) {    // JAL
    io.regWrite := true.B
    io.jal      := true.B
  }.elsewhen(io.opcode === "b1100111".U) {    // JALR
    io.regWrite := true.B
    io.jalr     := true.B
    io.aluSrc   := true.B
  }.elsewhen(io.opcode === "b0110111".U) {    // LUI
    io.regWrite := true.B
    io.lui      := true.B
    io.aluSrc   := true.B
  }.elsewhen(io.opcode === "b0010111".U) {    // AUIPC
    io.regWrite := true.B
    io.auipc    := true.B
    io.aluSrc   := true.B
  }
}