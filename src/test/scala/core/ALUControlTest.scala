import chisel3._
import chiseltest._
import org.scalatest.FreeSpec
import ALUOP._

class ALUControlTest extends FreeSpec with ChiselScalatestTester {

  "ALU Control" in {
    test(new ALUControl) { dut =>

      // ADD
      dut.io.opcode.poke("b0110011".U)
      dut.io.funct3.poke("b000".U)
      dut.io.funct7.poke(false.B)
      dut.io.aluOp.expect(ALU_ADD)

      // SUB
      dut.io.funct7.poke(true.B)
      dut.io.aluOp.expect(ALU_SUB)

      // AND
      dut.io.funct3.poke("b111".U)
      dut.io.aluOp.expect(ALU_AND)

      // OR
      dut.io.funct3.poke("b110".U)
      dut.io.aluOp.expect(ALU_OR)

      // Load = ADD
      dut.io.opcode.poke("b0000011".U)
      dut.io.aluOp.expect(ALU_ADD)
    }
  }
}