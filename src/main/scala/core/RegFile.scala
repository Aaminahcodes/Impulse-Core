import chisel3._
import chisel3.util._

trait RegConfig {
  val XLEN = 32
  val REGFILE_LEN = 32
}

class RegFileIO extends Bundle with RegConfig {
  val rs1 = Input(UInt(5.W))
  val rs2 = Input(UInt(5.W))

  val rdata1 = Output(UInt(XLEN.W))
  val rdata2 = Output(UInt(XLEN.W))

  val wen = Input(Bool())
  val waddr = Input(UInt(5.W))
  val wdata = Input(UInt(XLEN.W))
}

class RegFile extends Module with RegConfig {

  val io = IO(new RegFileIO)

  // Initialize all 32 registers to 0
  val regs = RegInit(VecInit(Seq.fill(REGFILE_LEN)(0.U(XLEN.W))))

  // Read register 1
  io.rdata1 := Mux(
    io.rs1 === 0.U,
    0.U,
    regs(io.rs1)
  )

  // Read register 2
  io.rdata2 := Mux(
    io.rs2 === 0.U,
    0.U,
    regs(io.rs2)
  )

  // Write register
  when(io.wen && io.waddr =/= 0.U) {
    regs(io.waddr) := io.wdata
  }

  // x0 is always 0
  regs(0) := 0.U
}