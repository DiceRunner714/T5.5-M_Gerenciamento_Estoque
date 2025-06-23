from model.item import Item

class Filial:
    def __init__(self, nome: str, local: str, id: int):
        self._nome: str = nome
        self._local: str = local
        self._id: int = id
        self._itens: list[Item] = []
    
    def adicionar_item(self, item: Item) -> None:
        self._itens.append(item)

    def remover_item(self, item: Item) -> None:
        if item in self._itens:
            self._itens.remove(item)

    def listar_caracteristicas(self) -> str:
        return f"ID: {self._id}\nNome: {self._nome}\nLocal: {self._local}\nQtd Itens: {len(self._itens)}"