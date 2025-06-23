from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import relationship
from config.database import Base

class EmpresaORM(Base):
    __tablename__ = "empresa"

    id = Column(Integer, primary_key=True, index=True)
    nome = Column(String(100), nullable=False)
    filiais = relationship("FilialORM", back_populates="empresa", cascade="all, delete")