from model.item import Item

class Filial:
    def __init__(self, nome: str, local: str, id: int):
        self._nome: str = nome
        self._local: str = local
        self._id: int = id
        self._itens: list[Item] = []