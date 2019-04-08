# Core code of pseudo random generator
# Author: Herrrb
# Time: 2018-10-15 19:05


def func_for_seq(first_num, size):
    """
    This function is for generating a sequence of `size` bytes.
    :param first_num: The first number of the sequence.
    :param size: The size of the sequence.
    :return: A pseudo random sequence.
    """
    l_seq = [first_num, ]
    size = int(size - 1)
    for i in range(size):
        lambda_ = 4
        l_seq.append(lambda_ * l_seq[i] * (1 - l_seq[i]))

    return l_seq


def func_for_select(first_num, size, a, b):
    """
    This function is for generating a sequence of `size` bytes for selecting.
    :param first_num: The first number of the selecting sequence.
    :param size: The size of the sequence.
    :param a: param.
    :param b: param.
    :return: A selecting sequence.
    """
    l_seq = [first_num, ]
    size = int(size - 1)
    for i in range(size):
        l_seq.append((a / l_seq[i] + 4 * l_seq[i] * (1 - l_seq[i])) % b)

    return l_seq


def func_for_choose(s1, s2, ss):
    """
    This function is for choosing bit from s1, s2 by ss.
    :param s1: The first sequence.
    :param s2: The second sequence.
    :param ss: The choosing sequence.
    :return: Sequence after choosing.
    """
    l_seq = []
    length = len(s1)
    for i in range(length):
        if ss[i] == 0:
            l_seq.append(s1[i])
        elif ss[i] == 1:
            l_seq.append(s2[i])

    return l_seq


def generator(s=2**10, f1=0.73, f2=0.45, fs=0.41, a=1000, b=1):
    """
    Pseudo random generator.
    :param s: Size of pseudo random sequence.
    :param f1: The first bytes of the first sequence.
    :param f2: The first bytes of the second sequence.
    :param fs: The first bytes of the selecting sequence.
    :param a: Param of selecting generator.
    :param b: Param of selecting generator.
    :return: A pseudo random sequence and number of 0 and 1.
    """
    seq_1 = func_for_seq(f1, s)
    seq_2 = func_for_seq(f2, s)
    seq_select = func_for_select(fs, s, a, b)
    # 将大于0.5的位用1代替，小于的用0代替
    seq_1_a = list(map(lambda x: 0 if x < 0.5 else 1, seq_1))
    seq_2_a = list(map(lambda x: 0 if x < 0.5 else 1, seq_2))
    seq_select_a = list(map(lambda x: 0 if x < 0.5 else 1, seq_select))
    # 生成最后的结果
    final_result = func_for_choose(seq_1_a, seq_2_a, seq_select_a)
    final_result = final_result[30:]
    # 统计01的个数
    num_of_0 = final_result.count(0)
    num_of_1 = final_result.count(1)
    # 返回byte形式的结果，以及01的个数
    final_result = list(map(lambda x: str(x), final_result))
    fr = ''.join(final_result)

    return fr, num_of_0, num_of_1
