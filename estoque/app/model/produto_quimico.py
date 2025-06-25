from model.item import Item

class ProdutoQuimico(Item):
    def __init__(self, nome: str, categoria: str, valor: float, quantidade: int, id: int, toxicidade: int):
        super().__init__(nome, categoria, valor, quantidade, id)
        self._toxicidade = toxicidade
        self.ajustarRestricao()

    def ajustarRestricao(self):
        self._restrito = self._toxicidade >= 7

    def restringir(self):
        if self._toxicidade < 7:
            raise Exception("Produto não é tóxico o suficiente para ser restrito.")
        self._restrito = True

    def liberar(self):
        if self._toxicidade >= 7:
            raise Exception("Produto tóxico não pode ser liberado.")
        self._restrito = False