package util.fileio

import model.ModelInterface

trait IOInterface {
  def load(modelInterface: ModelInterface): ModelInterface
  def save(modelInterface: ModelInterface)
}
