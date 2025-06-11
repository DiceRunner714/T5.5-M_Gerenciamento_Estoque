from model.filial import Filial

class Empresa:
    def __init__(self, nome: str):
        self._nome: str = nome
        self._filiais: list[Filial] = []