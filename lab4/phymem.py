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
    assert algorithm in {"fifo", "nru", "aging", "second-chance"}
    self.algorithm = algorithm

    if(algorithm == "fifo"):
      self.implementation = Fifo()
    if(algorithm == "nru"):
      self.implementation = NRU()
    if(algorithm == "second-chance"):
      self.implementation = SecondChance()

  def put(self, frameId):
    """Allocates this frameId for some page"""
    # Notice that in the physical memory we don't care about the pageId, we only
    # care about the fact we were requested to allocate a certain frameId
    self.implementation.put(self, frameId)

  def evict(self):
    """Deallocates a frame from the physical memory and returns its frameId"""
    # You may assume the physical memory is FULL so we need space!
    # Your code must decide which frame to return, according to the algorithm
    return self.implementation.evict(self)

  def clock(self):
    """The amount of time we set for the clock has passed, so this is called"""
    # Clear the reference bits (and/or whatever else you think you must do...)
    self.implementation.clock(self)

  def access(self, frameId, isWrite):
    """A frameId was accessed for read/write (if write, isWrite=True)"""
    self.implementation.access(self, frameId, isWrite)


class Fifo:
  def __init__(self):
    self.frames = []

  def put(self, frameId):
    self.frames.append(frameId)

  def evict(self):
    if(len(self.frames) > 0):
      return int(self.frames.pop(0))
    return 0

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    pass

class NRU:
  def __init__(self):
    self.frames = {}

  def put(self, frameId):
    self.frames[frameId] = {'r':1, 'm':0}

  def evict(self):
    if (len(self.frames) >= 1):
      for f in self.frames.keys():
        frame = self.frames[f]

        if (frame['r'] == 0 and frame['m'] == 0):
          return frames.pop(frame)
        if (frame['r'] == 1 and frame['m'] == 0):
          return frames.pop(frame)
        if (frame['r'] == 0 and frame['m'] == 1):
          return frames.pop(frame)
        if (frame['r'] == 1 and frame['m'] == 1):
          return frames.pop(frame)
    return 0

  def clock(self):
    for f in self.frames.keys():
      self.frames[f]['r'] = 0

  def access(self, frameId, isWrite):
    if(isWrite):
      frames[frameId] = {'r':1,'m':1}

class SecondChance:
  def __init__(self):
    self.frames = []
    self.r_bits = []

  def put(self, frameId):
    self.frames.append(frameId)
    self.r_bits.append(1)

  def evict(self):
    if(len(self.frames) > 0):
      bit = r_bits.pop(0)
      sc_frame = frames.pop(0)

      if(bit == 0)
        return sc_frame
      else
        frames.append(sc_frame)
        r_bits.append(0)
        return evict(self)
    return 0

  def clock(self):
    pass

  def access(self, frameId, isWrite):
    if(isWrite):
      frames[frameId] = 1