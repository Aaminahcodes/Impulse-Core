import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

trait MemConfig {
  val WLEN = 32
  val INST_MEM_LEN = 32
}

class InstMemIO extends Bundle with MemConfig {
  val addr = Input(UInt(WLEN.W))
  val inst = Output(UInt(WLEN.W))
}

class InstMem(initFile: String) extends Module with MemConfig {

  val io = IO(new InstMemIO)

  val imem = Mem(INST_MEM_LEN, UInt(WLEN.W))

  loadMemoryFromFile(imem, initFile)

  val index = io.addr(6, 2)

  io.inst := imem(index)
}