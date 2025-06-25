from model.item import Item

class ProdutoQuimico(Item):
    def __init__(self, nome: str, categoria: str, valor: float, quantidade: int, id: int, toxicidade: int):
        super().__init__(nome, categoria, valor, quantidade, id)
        self._toxicidade = toxicidade
        self.ajustarRestricao()