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
    
    def listar_itens(self) -> list[Item]:
        return self._itens.copy()
    
    def get_nome(self) -> str:
        return self._nome

    def set_nome(self, nome: str) -> None:
        self._nome = nome

    def get_local(self) -> str:
        return self._local

    def set_local(self, local: str) -> None:
        self._local = local

    def get_id(self) -> int:
        return self._id

    def set_id(self, id: int) -> None:
        self._id = id

    def __str__(self) -> str:
        return f"Filial {self._nome} (ID: {self._id}) - Local: {self._local}"