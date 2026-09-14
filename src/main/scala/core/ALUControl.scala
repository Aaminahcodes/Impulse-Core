import chisel3._
import chisel3.util._
import ALUOP._

class ALUControl extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))
    val funct3 = Input(UInt(3.W))
    val funct7 = Input(Bool())
    val aluOp  = Output(SInt(5.W))
  })

  io.aluOp := ALU_ADD

  when(io.opcode === "b0110011".U) {  // R-type
    switch3(io.funct3, io.funct7)
  }.elsewhen(io.opcode === "b0010011".U) {  // I-type ALU
    switch3(io.funct3, io.funct7)
  }.elsewhen(io.opcode === "b0000011".U) {  // Load
    io.aluOp := ALU_ADD
  }.elsewhen(io.opcode === "b0100011".U) {  // Store
    io.aluOp := ALU_ADD
  }.elsewhen(io.opcode === "b1100011".U) {  // Branch
    io.aluOp := ALU_SUB
  }.elsewhen(io.opcode === "b1101111".U || io.opcode === "b1100111".U) { // JAL/JALR
    io.aluOp := ALU_ADD
  }.elsewhen(io.opcode === "b0110111".U || io.opcode === "b0010111".U) { // LUI/AUIPC
    io.aluOp := ALU_ADD
  }

  // Helper for funct3 decoding
  def switch3(f3: UInt, f7: Bool): Unit = {
    when(f3 === "b000".U) {
      io.aluOp := Mux(f7, ALU_SUB, ALU_ADD)
    }.elsewhen(f3 === "b001".U) {
      io.aluOp := ALU_SLL
    }.elsewhen(f3 === "b010".U) {
      io.aluOp := ALU_SLT
    }.elsewhen(f3 === "b011".U) {
      io.aluOp := ALU_SLTU
    }.elsewhen(f3 === "b100".U) {
      io.aluOp := ALU_XOR
    }.elsewhen(f3 === "b101".U) {
      io.aluOp := Mux(f7, ALU_SRA, ALU_SRL)
    }.elsewhen(f3 === "b110".U) {
      io.aluOp := ALU_OR
    }.elsewhen(f3 === "b111".U) {
      io.aluOp := ALU_AND
    }
  }
}