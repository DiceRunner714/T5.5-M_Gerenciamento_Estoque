from model.filial import Filial

class Empresa:
    def __init__(self, nome: str):
        self._nome: str = nome
        self._filiais: list[Filial] = []

    def adicionar_filial(self, nome: str, local: str, id: int) -> None:
        nova_filial = Filial(nome, local, id)
        self._filiais.append(nova_filial)

    
    def buscar_filial(self, id: int) -> Filial | None:
        for filial in self._filiais:
            if filial.id == id:
                return filial
        return None