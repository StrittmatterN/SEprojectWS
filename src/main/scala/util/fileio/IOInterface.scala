package util.fileio

import model.{GameTable, ModelInterface}

trait IOInterface {
  def load(modelInterface: ModelInterface): ModelInterface
  def save(modelInterface: ModelInterface)
}
