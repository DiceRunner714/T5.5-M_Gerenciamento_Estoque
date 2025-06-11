from pydantic import BaseModel
from typing import Optional

class EmpresaBase(BaseModel):
    nome: str

class EmpresaCreate(EmpresaBase):
    pass

class EmpresaResponse(EmpresaBase):
    id: int

    class Config:
        orm_mode = True
