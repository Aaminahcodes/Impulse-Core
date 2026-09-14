import chisel3._

object ALUOP {
  val ALU_ADD  = 0.S(5.W)
  val ALU_SUB  = 1.S(5.W)
  val ALU_AND  = 2.S(5.W)
  val ALU_OR   = 3.S(5.W)
  val ALU_XOR  = 4.S(5.W)
  val ALU_SLT  = 5.S(5.W)
  val ALU_SLL  = 6.S(5.W)
  val ALU_SLTU = 7.S(5.W)
  val ALU_SRL  = 8.S(5.W)
  val ALU_SRA  = 9.S(5.W)
  val ALU_MUL  = 10.S(5.W)
  val ALU_DIV  = 11.S(5.W)
  val ALU_XXX  = 15.S(5.W)
}

trait Config {
  val WLEN = 32
  val ALUOP_SIG_LEN = 5
}