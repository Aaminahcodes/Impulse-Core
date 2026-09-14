import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class TOPTest extends FreeSpec with ChiselScalatestTester {

  "Complete RISC-V Datapath" in {

    test(new TOP("src/test/scala/core/Instructions.hex")) { dut =>

     
      dut.clock.step(100)
      dut.io.pc.expect(400.U)

      
    }
  }
}