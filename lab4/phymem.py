# coding=utf-8

from random import randint

# This is the only file you must implement

# This file will be imported from the main code. The PhysicalMemory class
# will be instantiated with the algorithm received from the input. You may edit
# this file as you which

# NOTE: there may be methods you don't need to modify, you must decide what
# you need...

class PhysicalMemory:
  ALGORITHM_AGING_NBITS = 8
  """How many bits to use for the Aging algorithm"""

  def __init__(self, algorithm):
    assert algorithm in {"fifo", "lru", "nru", "aging", "second-chance", "belady"}
    self.algorithm = algorithm;

    if (algorithm == "fifo"):
      self.implementation = Fifo()
    elif (algorithm == "lru"):
      self.implementation = LRU()
    elif (algorithm == "nru"):
      self.implementation = NRU()
    elif (algorithm == "aging"):
      self.implementation = Aging()
    elif (algorithm == "second-chance"):
      self.implementation = SecondChance()
    elif (algorithm == "belady"):
      self.implementation = Belady()

  def put(self, frameId):
    """Allocates this frameId for some page"""
    # Notice that in the physical memory we don't care about the pageId, we only
    # care about the fact we were requested to allocate a certain frameId
    self.implementation.put(frameId)

  def evict(self):
    """Deallocates a frame from the physical memory and returns its frameId"""
    # You may assume the physical memory is FULL so we need space!
    # Your code must decide which frame to return, according to the algorithm
    return self.implementation.evict()

  def clock(self):
    """The amount of time we set for the clock has passed, so this is called"""
    # Clear the reference bits (and/or whatever else you think you must do...)
    self.implementation.clock()

  def access(self, frameId, isWrite):
    """A frameId was accessed for read/write (if write, isWrite=True)"""
    self.implementation.access(frameId, isWrite)

#First In First Out
class Fifo:
  def __init__(self):
    self.frames = [] #Array

  def put(self, frameId):
    self.frames.append(frameId) #Append normal - Entra no final da fila

  def evict(self):
    if(len(self.frames) > 0):
      return int(self.frames.pop(0)) # remove o primeiro da fila
    return 0

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    pass

#Least Recent Used
class LRU:
  def __init__(self):
    self.frames = [] # Array dos frames

  def put(self, frameId):
    self.frames.append(frameId) # Salva o frame no final

  def evict(self):
    if(len(self.frames) > 0):
      return self.frames.pop(0) #Tira o primeiro frame
    return 0

  def clock(self):
    pass

  def access(self, frameId, isWrite): #Sempre que usar, remove e coloca no final, aí os primeiros
    self.frames.remove(frameId)       #são os que foram menos usados 
    self.frames.append(frameId)

#Not Recently Used
class NRU:
  def __init__(self):
    self.frames = [] #Array de arrays [frameId, referenced, modifies]

  def put(self, frameId):
    self.frames.append([frameId, 1, 1]) #Dá append na página com bit de referência e de modificado 1

  def evict(self):
    if(len(self.frames) > 0):
      self.frames.sort(key=lambda pf: pf[2])  # Ordena a partir do segundo dígito (modificado)
      self.frames.sort(key=lambda pf: pf[1])  # Ordena a partir do primeiro dígito (referenciado)                                 
      return self.frames.pop(0)[0] #Retorna o primeiro, o de menor prioridade naquela ordem
    return 0

  def clock(self):
    for frame in self.frames:
      frame[1] = 0 #Limpa os bits de referencia

  def access(self, frameId, isWrite):
    for frame in self.frames:
      if frame[0] == frameId:
        frame[1] = 1 #Seta pra referenciado
        frame[2] = isWrite #Se write for 1, seta pra modificado, se write for 0, seta pra não modificado huaahu

#Aging
class Aging:
  def __init__(self):
    self.page_frames = [] #Array de arrays [frameId, contador]

  def put(self, frameId):
    self.page_frames.append([frameId, 0]) #Append normal, contador em 0

  def evict(self):
    self.page_frames.sort(key=lambda pf: pf[1]) #Faz o sort e pega o primeiro, que seria o menor número no contador

    return self.page_frames.pop(0)[0]

  def clock(self):
    for p_frame in self.page_frames:
      p_frame[1] >>= 1 #faz o shift, p_frame[1] faz shift de 1 casa pra direita

  def access(self, frameId, isWrite):
    for p_frame in self.page_frames:
      if p_frame[0] == frameId:
        p_frame[1] >>= 1 #faz shift, p_frame[1] faz shift de 1 casa pra direita
        p_frame[1] |= 256 #OR binario e faz com que o contador do frame seja igual ao resultado

#Second Chance
class SecondChance:
  def __init__(self):
    self.frames = []

  def put(self, frameId):
    self.frames.append([frameId, 1])

  def evict(self):
    pageFrame = self.frames.pop(0)

    while pageFrame[1]:
      self.frames.append([pageFrame[0], 0])
      pageFrame = self.frames.pop(0)

    return pageFrame[0]

  def clock(self):
    pass
  
  def access(self, frameId, isWrite):
    for f_id in self.frames:
      if frameId == f_id[0]:
        f_id[1] = 1

#Belady
class Belady:
  def __init__(self):
    self.frames = {}

  def put(self, frameId):
    label = randint(0,10000)
    self.frames[frameId] = label

  def evict(self):
    if(len(self.frames) > 0):
      highest_label = 0
      frame_id_highest_label = 0
      for frameId in self.frames:
        if(self.frames[frameId] > highest_label):
          highest_label = self.frames[frameId]
          frame_id_highest_label = frameId
    return frame_id_highest_label

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    pass