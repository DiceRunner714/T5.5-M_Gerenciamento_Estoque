from model.item import Item


class Farmaceutico(Item):
    def __init__(self, nome: str, id: int, valor: float, quantidade: int, crf: str):
        super().__init__(nome, valor, quantidade, id)
        self._crf = crf
        self.ajustarRestricao()
