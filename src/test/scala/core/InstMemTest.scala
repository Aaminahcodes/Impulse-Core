import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class InstMemTest extends FreeSpec with ChiselScalatestTester {

  "Instruction Memory" in {
    test(new InstMem("src/test/scala/Instructions.hex")) { dut =>

      dut.io.addr.poke(0.U)
      dut.io.inst.expect("h00000013".U)

      dut.io.addr.poke(4.U)
      dut.io.inst.expect("h00100093".U)

      dut.io.addr.poke(8.U)
      dut.io.inst.expect("h00200113".U)

      dut.io.addr.poke(12.U)
      dut.io.inst.expect("h00308193".U)

      dut.io.addr.poke(16.U)
      dut.io.inst.expect("h00408213".U)
    }
  }
}