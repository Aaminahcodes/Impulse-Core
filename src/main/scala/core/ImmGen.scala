import chisel3._
import chisel3.util._

class immGenIO extends Bundle {
  val instr  = Input(UInt(32.W))
  val immd_se = Output(UInt(32.W))
}

class immGen extends Module {
  val io = IO(new immGenIO)

  val opcode = io.instr(6, 0)

  io.immd_se := 0.U

  when(opcode === "b0010011".U || opcode === "b0000011".U || opcode === "b1100111".U) {
    // I-type
    io.immd_se := Cat(Fill(20, io.instr(31)), io.instr(31, 20))
  }.elsewhen(opcode === "b0100011".U) {
    // S-type
    io.immd_se := Cat(Fill(20, io.instr(31)), io.instr(31, 25), io.instr(11, 7))
  }.elsewhen(opcode === "b1100011".U) {
    // B-type
    io.immd_se := Cat(
      Fill(19, io.instr(31)),
      io.instr(31),
      io.instr(7),
      io.instr(30, 25),
      io.instr(11, 8),
      0.U(1.W)
    )
  }.elsewhen(opcode === "b0110111".U || opcode === "b0010111".U) {
    // U-type
    io.immd_se := Cat(io.instr(31, 12), 0.U(12.W))
  }.elsewhen(opcode === "b1101111".U) {
    // J-type
    io.immd_se := Cat(
      Fill(11, io.instr(31)),
      io.instr(31),
      io.instr(19, 12),
      io.instr(20),
      io.instr(30, 21),
      0.U(1.W)
    )
  }
}