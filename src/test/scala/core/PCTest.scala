import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class PCTest extends FreeSpec with ChiselScalatestTester {

  "PC" in {
    test(new PC) { dut =>

      dut.io.nextPC.poke(0.U)
      dut.io.pc.expect(0.U)
      dut.io.pcPlus4.expect(4.U)

      dut.io.nextPC.poke(20.U)
      dut.clock.step()

      dut.io.pc.expect(20.U)
      dut.io.pcPlus4.expect(24.U)
    }
  }
}