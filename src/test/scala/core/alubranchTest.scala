import chisel3._
import chiseltest._
import org.scalatest.FreeSpec

class alubranchTest extends FreeSpec with ChiselScalatestTester {

  "Branch Unit" in {
    test(new alubranch) { dut =>

      dut.io.branch.poke(true.B)

      // BEQ
      dut.io.func3.poke(0.U)
      dut.io.data_1.poke(5.U)
      dut.io.data_2.poke(5.U)
      dut.io.br_taken.expect(true.B)

      // BNE
      dut.io.func3.poke(1.U)
      dut.io.data_2.poke(3.U)
      dut.io.br_taken.expect(true.B)

      // BLT
      dut.io.func3.poke(4.U)
      dut.io.data_1.poke(2.U)
      dut.io.data_2.poke(5.U)
      dut.io.br_taken.expect(true.B)

      // BGE
      dut.io.func3.poke(5.U)
      dut.io.data_1.poke(5.U)
      dut.io.data_2.poke(2.U)
      dut.io.br_taken.expect(true.B)
    }
  }
}